'use strict';

angular.module('userUi').filter('ticketStatus', function (EnumTicketStatus) {
  return function (input) {
    var result = _.findWhere(EnumTicketStatus, {id: input});
    return result && result.label;
  };
});
