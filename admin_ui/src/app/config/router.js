"use strict";

angular.module('adminUi').config(function ($locationProvider, $stateProvider, $urlRouterProvider) {
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
  $stateProvider.state('ticket.ticket.index', {
    url: '/index',
    label: 'state_label_ticket',
    controller: 'TicketIndexCtrl as vm',
    templateUrl: 'app/ticket/view/index.html'
  });
  $stateProvider.state('ticket.ticket.ticketInfo', {
    url: '/ticketInfo?id',
    label: 'state_label_ticketInfo',
    controller: 'TicketInfoCtrl as vm',
    templateUrl: 'app/ticket/view/ticketInfo.html'
  });
  $stateProvider.state('ticket.login', {
    url: '/login',
    label: '',
    controller: 'LoginCtrl as vm',
    templateUrl: 'app/authentication/view/login.html'
  });
  $stateProvider.state('ticket.register', {
    url: '/register',
    label: '',
    controller: 'RegisterCtrl as vm',
    templateUrl: 'app/authentication/view/register.html'
  });

  $urlRouterProvider.otherwise('/ticket/index');

});
