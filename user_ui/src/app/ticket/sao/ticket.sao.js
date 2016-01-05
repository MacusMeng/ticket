"use strict";
angular.module('userUi').factory('Ticket', function TicketFactory($resource) {
  return $resource('/ticket/api/users/user-tickets/:ticketId', {ticketId: '@ticketId'}, {
    update: {
      method: 'PUT'
    },
    query: {
      isArray: false
    }
  });
});

angular.module('userUi').factory('TicketIdentity', function TicketIdentityFactory($resource) {
  return $resource('/ticket/api/users/user-tickets/identity');
});

angular.module('userUi').factory('TicketFiles', function TicketFilesFactory($resource) {
  return $resource('/ticket/api/users/user-tickets/files');
});

angular.module('userUi').factory('TicketFile', function TicketFileFactory($resource) {
  return $resource('/ticket/api/users/user-tickets/:ticketId/files/:fileName/delete', {
    ticketId: '@ticketId',
    fileName: '@fileName'
  });
});
