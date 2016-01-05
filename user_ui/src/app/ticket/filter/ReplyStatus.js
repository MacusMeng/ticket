'use strict';

angular.module('userUi').filter('replyStatus', function (EnumReplyStatus) {
  return function (input) {
    var result = _.findWhere(EnumReplyStatus, {id: input});
    return result && result.label;
  };
});
