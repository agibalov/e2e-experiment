import 'core-js/es6/reflect';
import 'core-js/es7/reflect';
import 'zone.js/dist/zone';
import 'rxjs/Rx';

import {Component, NgModule, Injectable, OnInit} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {HttpModule, Http} from "@angular/http";
import {FormsModule} from "@angular/forms";

interface Todo {
    id: number;
    text: string;
}

@Injectable()
class ApiClient {
    constructor(private http: Http) {
    }

    async getTodos(): Promise<Todo[]> {
        const response = await this.http.get('/api/todos/').toPromise();
        return <Todo[]>response.json();
    }

    async createTodo(text: string): Promise<void> {
        await this.http.post('/api/todos/', {
            text: text
        }).toPromise();
    }

    async deleteTodo(id: number): Promise<void> {
        await this.http.delete(`/api/todos/${id}`).toPromise();
    }
}

@Component({
    selector: 'app',
    template: `<h1>Todo list</h1>
<form (ngSubmit)="createTodo()">
    <input type="text" [(ngModel)]="newTodoText" name="newTodoText" autocomplete="off">
    <button type="submit">Create</button>
</form>
<ul *ngIf="todos.length > 0">    
    <li *ngFor="let todo of todos;trackBy:id">
        <span>{{todo.id}}</span>
        <span>{{todo.text}}</span>
        <button type="button" (click)="deleteTodo(todo.id)">Delete</button>
    </li>
</ul>
<div *ngIf="todos.length == 0">There are no todos</div>
`
})
class AppComponent implements OnInit {
    newTodoText: string = '';
    todos: Todo[] = [];

    constructor(private apiClient: ApiClient) {
    }

    async ngOnInit(): Promise<void> {
        this.todos = await this.apiClient.getTodos();
    }

    async createTodo(): Promise<void> {
        const text = this.newTodoText;
        if(text == '') {
            return;
        }

        this.newTodoText = '';

        await this.apiClient.createTodo(text);
        this.todos = await this.apiClient.getTodos();
    }

    async deleteTodo(id: number): Promise<void> {
        await this.apiClient.deleteTodo(id);
        this.todos = await this.apiClient.getTodos();
    }
}

@NgModule({
    imports: [ BrowserModule, HttpModule, FormsModule ],
    declarations: [ AppComponent ],
    providers: [ ApiClient ],
    bootstrap: [ AppComponent ]
})
class AppModule {
}

platformBrowserDynamic().bootstrapModule(AppModule);
