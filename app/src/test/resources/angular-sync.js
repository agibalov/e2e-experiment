var done = arguments[0];
window.getAngularTestability(document.querySelector('app'))
    .whenStable(function(didWork) {
        done(didWork);
    });
