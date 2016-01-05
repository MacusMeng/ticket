'use strict';
angular.module('userUi').factory('ReplyIdentity', function ReplyIdentityFactory($resource) {
    return $resource('/ticket/api/users/user-tickets/identity');
});

angular.module('userUi').factory('Replies', function RepliesFactory($resource) {
    return $resource('/ticket/api/users/user-replies/:replyId', {replyId: '@replyId'}, {
        update: {
            method: 'PUT'
        },
        query: {
            isArray: false
        }
    });
});

