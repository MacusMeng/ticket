'use strict';
angular.module('adminUi').controller('TicketInfoCtrl', function TicketIndexCtrl($scope, Tickets, $state, Replies, $stateParams, ReplyIdentity, $window) {
  var vm = this;
  vm.form = {
    ticketId: $stateParams.id,
    content: undefined,
    id: undefined
  };
  vm.alerts = [];
  vm.isDisabled = false;

  get(vm.form.ticketId);

  function get(id) {
    Tickets.get({id: id}, function (ticket) {
      vm.ticket = ticket;
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

  $scope.$on("error", function (event, args) {
    vm.alerts.push(args);
    event.stopPropagation();
  });

  vm.interacted = function (form, field) {
    return form.$submitted || field.$dirty;
  };

  vm.isShow = function () {
    return vm.ticket && vm.ticket.status != "CLOSED";
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
        vm.isDisabled = false;
      }, function (error) {
        $scope.$emit("error", error.data.message ? error.data.message : '');
        vm.isDisabled = false;
      });
    }

  };

  vm.isImage = function (file) {
    return _.includes(file.contentType, 'image');
  };

  vm.url = function (file) {
    return "/ticket-admin/api/tickets/" + vm.form.ticketId + "/files/" + file.fileName + "/get";
  };

  vm.docImage = function () {
    if ($window.location.hostname === 'localhost') {
      return '../assets/images/doc.png';
    } else {
      return "/ticket-admin/assets/images/doc.png"
    }
  };
});

