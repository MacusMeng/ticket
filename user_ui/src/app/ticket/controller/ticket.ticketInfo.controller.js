'use strict';
angular.module('userUi').controller('TicketUserCtrl', function TicketUserCtrl($window, $scope, Ticket, $state, Replies, $stateParams, ReplyIdentity) {
  var vm = this;
  vm.form = {
    ticketId: $stateParams.id,
    content: undefined
  };
  vm.alerts = [];

  vm.isClosed = false;
  vm.isDisabled=false;

  $scope.$on("error", function (event, args) {
    vm.alerts.push(args);
    event.stopPropagation();
  });

  get(vm.form.ticketId);

  function get(id) {
    Ticket.get({ticketId: id}, function (ticket) {
      vm.ticket = ticket;
    }, function (error) {
      $scope.$emit("error", error.data.message ? error.data.message : '');
    });
  }

  vm.tinymceOptions = {
    plugins: "advlist preview insertdatetime save emoticons paste textcolor autoresize autosave",
    toolbar: "styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | preview | forecolor backcolor insertdatetime emoticons",
    menubar: false,
    statusbar: false,
    format: 'html',
    skin: 'light'
  };

  vm.interacted = function (form, field) {
    return form.$submitted || field.$dirty;
  };

  vm.isShow = function () {
    return vm.ticket && vm.ticket.status != "CLOSED";
  };

  vm.close = function () {
    if(vm.isClosed){
      return;
    }
    vm.isClosed = true;
    Ticket.update({ticketId: vm.form.ticketId}, function () {
      $state.go('ticket.ticket.index');
      vm.isClosed =false;
    }, function (error) {
      $scope.$emit("error", error.data.message ? error.data.message : '');
      vm.isClosed=false;
    });
  };
  vm.back=function(){
    $state.go('ticket.ticket.index');
  };

  vm.submit = function (form) {
    if (form.$valid) {
      if(vm.isDisabled){
        return;
      }
      vm.isDisabled = true;
      Replies.save(vm.form, function () {
        get(vm.form.ticketId);
        vm.form.content = undefined;
        form.$setPristine();
        vm.isDisabled=false;
      }, function (error) {
        $scope.$emit("error", error.data.message ? error.data.message : '');
        vm.isDisabled=false;
      });
    }

  };

  vm.replyButton=function(status) {
    if (status == 'REPLIED' || status == 'USER_REPLY') {
      return true;
    }
  };

  vm.submitButton=function(status){
    if(status == 'CREATED'){
      return true;
    }
  };

  vm.isImage = function (file) {
    return _.includes(file.contentType, 'image');
  };

  vm.url = function (file) {
    return "/ticket/api/users/user-tickets/" + vm.form.ticketId + "/files/" + file.fileName + "/get";
  };

  vm.docImage = function () {
    if ($window.location.hostname === 'localhost') {
      return '../assets/images/doc.png';
    } else {
      return "/ticket/assets/images/doc.png"
    }
  };
});

