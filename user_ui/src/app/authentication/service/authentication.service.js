'use strict';

angular.module('userUi').service('authentication', function () {
    var toState;
    var currentUser;

    this.getState = function () {
        return toState;
    };

    this.setState = function (state) {
        toState = state;
    };

    this.setCurrentUser = function (user) {
        currentUser = user;
    };

    this.getCurrentUser = function () {
        return currentUser;
    };
});
