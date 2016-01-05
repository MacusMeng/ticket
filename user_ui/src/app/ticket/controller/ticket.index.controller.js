'use strict';
angular.module('userUi').controller('TicketIndexCtrl', function TicketIndexCtrl($scope, Ticket, EnumReplyStatus) {
  var vm = this;
  vm.form = {
    number: undefined,
    status: undefined,
    page: 1,
    pageSize: 10
  };
  vm.alerts = [];

  var tabs = angular.copy(EnumReplyStatus);
  tabs.unshift({id: undefined, label: 'select_option_any_ticket_status'});
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
      Ticket.query(vm.form, function (tickets) {
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

  vm.validateNumber = function(ticket_id){
    return !ticket_id ? true : ticket_id && (/^[0-9]+$/.test(ticket_id));
  };

  vm.select = function (status,form) {
    if(status==undefined){
      vm.form.status=status;
    }
    else {
      vm.form.status = status == 'REPLIED' ||
      status == 'USER_REPLY' ? 'REPLIED'+','+'USER_REPLY' : status;
    }
    query(form);
  }
});

