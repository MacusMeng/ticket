'use strict';

angular.module('adminUi').filter('ticketModule', function (EnumTicketModule) {
  return function (input) {
    var result = _.findWhere(EnumTicketModule, {id: input});
    return result && result.label;
  };
});
