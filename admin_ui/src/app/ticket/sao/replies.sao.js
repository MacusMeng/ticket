'use strict';
angular.module('adminUi').factory('ReplyIdentity', function ReplyIdentityFactory($resource) {
  return $resource('/ticket-admin/api/replies/identity');
});

angular.module('adminUi').factory('Replies', function RepliesFactory($resource) {
  return $resource('/ticket-admin/api/replies/:replyId', {replyId: '@replyId'}, {
    update: {
      method: 'PUT'
    },
    query: {
      isArray: false
    }
  });
});
