"use strict";

angular.module('userUi').config(function ($locationProvider, $stateProvider, $urlRouterProvider, $translateProvider) {
  $stateProvider.state('ticket', {
    abstract: true,
    url: '',
    label: '',
    controller: 'LayoutCtrl as vm',
    templateUrl: 'app/layout/layout.html'
  });
  $stateProvider.state('ticket.ticket', {
    abstract: true,
    url: '/ticket',
    label: 'state_label_ticket',
    template: '<div ui-view></div>'
  });
  $stateProvider.state('ticket.ticket.add', {
    url: '/add',
    label: 'state_label_ticket_add',
    controller: 'TicketAddCtrl as vm',
    templateUrl: 'app/ticket/view/add.html'
  });
  $stateProvider.state('ticket.ticket.index', {
    url: '/index',
    label: 'state_label_ticket_index',
    controller: 'TicketIndexCtrl as vm',
    templateUrl: 'app/ticket/view/index.html'
  });
  $stateProvider.state('ticket.ticket.ticketInfo', {
    url: '/ticketInfo?id',
    label: 'state_label_ticketInfo',
    controller: 'TicketUserCtrl as vm',
    templateUrl: 'app/ticket/view/ticketInfo.html'
  });
  $urlRouterProvider.otherwise('/ticket/add');

});
