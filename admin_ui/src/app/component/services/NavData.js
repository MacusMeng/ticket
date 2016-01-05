"use strict";
angular.module('adminUi').service('navData', function NavData($state, $filter, authentication) {
  var self = this;
  var $translate = $filter('translate');
  this.current = {};
  this.label = function (state) {
    return $translate($state.get(state.state).label);
  };
  this.currentParentLabel = function () {
    var result = undefined;
    _.forEach(this.menus, function (item) {
      if (item.state === self.current.state.name) {
        return result;
      } else {
        _.forEach(item.children, function (subItem) {
          if (subItem.state === self.current.state.name) {
            result = $translate(item.label);
          }
        })
      }
    });

    return result;
  };
  this.menus = [
    {
      state: 'ticket.ticket.index',
      icon: 'home',
      children: [],
      label: ''
    }
  ];

});
