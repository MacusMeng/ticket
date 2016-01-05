'use strict';

angular.module('adminUi').filter('ticketErrors', function (EnumTicketErrors) {
  return function (input) {
    var result = _.findWhere(EnumTicketErrors, {id: input});
    return result ? result.label : 'error_message_unknown';
  };
});
