'use strict';

angular.module('userUi').filter('modules', function (EnumModules) {
  return function (input) {
    var result = _.findWhere(EnumModules, {id: input});
    return result && result.label;
  };
});
