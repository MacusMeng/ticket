"use strict";
angular.module('adminUi').controller('RegisterCtrl', function RegisterCtrl($scope, $state, $translate, $location, Login, Register, authentication, EnumTicketModule) {
  var vm = this;
  vm.alerts = [];

  vm.signupform = {
    username: undefined,
    password: undefined,
    email: undefined,
    selectedModules: []
  };

  vm.modules = _.filter(EnumTicketModule, function (module) {
    return module.id != 'OTHER';
  });

  vm.changeLanguage = function (langKey) {
    $translate.use(langKey);
  };

  vm.register = function (form) {
    if (form.$valid) {
      Register.save({
        username: vm.signupform.username,
        password: vm.signupform.passwd,
        email: vm.signupform.email,
        modules: _.map(vm.signupform.selectedModules, function (module) {
          return module.id;
        })
      }, function () {
        $state.go("ticket.ticket.index");
      }, function (error) {
        $scope.$emit("error", error.data.message ? error.data.message : '');
      })
    }
  };

  vm.interacted = function (form, field) {
    return form.$submitted || field.$dirty;
  };

  $scope.$on("error", function (event, args) {
    vm.alerts.push(args);
    event.stopPropagation();
  });

  vm.openLogin = function () {
    $state.go('ticket.login');
  };

  vm.validateUsername = function (username) {
    return /^[a-zA-Z_][a-zA-Z0-9_]{5,}$/.test(username);
  };

  vm.validatePassword = function (password) {
    return password && password.length >= 8 && !(/^[0-9]+$/.test(password));
  }
});
