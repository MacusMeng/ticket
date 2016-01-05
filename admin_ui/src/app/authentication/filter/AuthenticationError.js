'use strict';

angular.module('adminUi').filter('authenticationError', function (EnumAuthenticationErrors) {
  return function (input) {
    var result = _.findWhere(EnumAuthenticationErrors, {id: input});
    return result ? result.label : 'error_message_unknown';
  };
});
