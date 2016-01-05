'use strict';

angular.module('adminUi').factory('Tickets', function TicketsFactory($resource) {
  return $resource('/ticket-admin/api/tickets/:id', {id: '@id'}, {
    update: {
      method: 'PUT'
    },
    query: {
      isArray: false
    }
  });
});

