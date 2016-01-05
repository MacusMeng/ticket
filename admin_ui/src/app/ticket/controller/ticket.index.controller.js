'use strict';
angular.module('adminUi').controller('TicketIndexCtrl', function TicketIndexCtrl($scope, Tickets, EnumTicketStatus) {
  var vm = this;
  vm.form = {
    status: undefined,
    page: 1,
    pageSize: 10
  };
  vm.alerts=[];

  var tabs = angular.copy(EnumTicketStatus);
  tabs.unshift({id: undefined, label: 'select_option_any_metrics'});
  vm.tabs = tabs;

  $scope.$on("error", function (event, args) {
    vm.alerts.push(args);
    event.stopPropagation();
  });

  vm.query = function (form) {
    query(form);
  };

  function query(form) {
    if(form.$valid) {
      Tickets.query(vm.form, function (tickets) {
        vm.tickets = tickets.content;
        vm.totalItems = tickets.totalElements;
        vm.totalPages = tickets.totalPages;
      }, function (error) {
        $scope.$emit("error", error.data.message ? error.data.message : '');
      });
    }
  }

  vm.interacted = function (form, field) {
    return form.$submitted || field.$dirty;
  };

  vm.validateTicketNumber = function(ticket_id){
    return !ticket_id ? true : ticket_id && (/^[0-9]+$/.test(ticket_id));
  }
  vm.select = function (status,form) {
    vm.form.status = status;
      query(form);
  }
});
