"use strict";
angular.module('userUi').controller('TicketAddCtrl', function TicketAddCtrl(EnumModules,$modal, $scope, Upload, TicketIdentity, Ticket, TicketFile, $state, $location, $window, FileTypes) {
  var vm = this;
  vm.modules = EnumModules;
  vm.form = {
    title: undefined,
    module: undefined,
    content: undefined,
    id: undefined
  };
  vm.uploadedFiles = [];
  vm.alerts = [];
  vm.fileTypes = FileTypes;
  vm.isDisabled = false;

  console.log($window.parent.location);

  $scope.$on("error", function (event, args) {
    vm.alerts.push(args);
    event.stopPropagation();
  });

  TicketIdentity.save(function (identity) {
    vm.form.id = identity.id;
  }, function (error) {
    $scope.$emit("error", error.data.message ? error.data.message : '');
  });

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

   function fileCheckout(size,isSize,isType){
    var modalInstance = $modal.open({
      animation: true,
      templateUrl: 'FileCheck',
      controller: 'FileCtrl as vm',
      size: size,
      resolve:{
        isSize :function(){
          return isSize
        },
        isType :function(){
          return isType
        }
      }
    });
    modalInstance.result.then(function () {
    }, function () {
    });
  }

  vm.upload = function (file) {
    if (!file) {
      return;
    }

    if (!checkType(file)) {
      return;
    }

    if (exist(file)) {
      return;
    }

    Upload.upload({
      url: '/ticket/api/users/user-tickets/files',
      fields: {'ticketId': vm.form.id, 'fileName': file.name},
      file: file
    }).progress(function (evt) {

    }).success(function (data, status, headers, config) {
      vm.uploadedFiles.push(file);
    }).error(function (error, status, headers, config) {
      $scope.$emit("error", error.message ? error.message : '');
    })
  };

    $scope.alerts = [];

    vm.addAlert = function() {
      vm.alerts.push({msg: "Another alert!"});
    };

    vm.closeAlert = function(index) {
      vm.alerts.splice(index, 1);
    };
  vm.submit = function (form) {
    if (form.$valid) {
      if(vm.isDisabled){
        return;
      }
      vm.isDisabled = true;
      var request = {
        id: vm.form.id,
        title: vm.form.title,
        module: vm.form.module.id,
        content: vm.form.content
      };

      Ticket.save(request, function () {
        if ($window.location === $window.parent.location) {
          $state.go('ticket.ticket.index');
        } else {
          $window.parent.location.href = $window.parent.location.href.replace($window.parent.location.hash, '#/system/myTicket/');
        }
        vm.isDisabled = false;
      }, function (error) {
        $scope.$emit("error", error.data.message ? error.data.message : '');
        vm.isDisabled = false;
      });
    }

  };

  vm.disableUpload = function () {
    return vm.uploadedFiles.length >= 3;
  };

  vm.isImage = function (file) {
    return _.includes(file.type, 'image');
  };

  vm.remove = function (file) {
    TicketFile.delete({ticketId: vm.form.id, fileName: file.name}, function () {
      _.remove(vm.uploadedFiles, function (uploadedFile) {
        return uploadedFile.name === file.name;
      })
    }, function (error) {
      $scope.$emit("error", error.data.message ? error.data.message : '');
    });
  };

  vm.docImage = function () {
    if ($window.location.hostname === 'localhost') {
      return '../assets/images/doc.png';
    } else {
      return "/ticket/assets/images/doc.png"
    }
  };

  function exist(file) {
    return _.filter(vm.uploadedFiles, function (uploadedFile) {
        return uploadedFile.name === file.name;
      }).length > 0;
  }

  function checkType(file) {
    if(_.includes(['text/plain','image/bmp','image/png','image/jpeg'],file.type)){
      if(file.size>=2097152){
        fileCheckout("md",true,false);
        return;
      }
      return true;
    }
    else{
      if (!file.type) {
        if (_.includes(file.name, ".zip") || _.includes(file.name, ".doc") || _.includes(file.name, ".docx") || _.includes(file.name, ".rar")) {
          if (file.size >= 2097152) {
            fileCheckout("md", true, false);
            return;
          } else {
            return true;
          }
        } else {
          if (file.size >= 2097152) {
            fileCheckout("md", true, true);
            return;
          }
          else {
            fileCheckout("md", false, true);
            return;
          }
        }
      }
      else{
        if (file.size >= 2097152) {
          fileCheckout("md", true, false);
          return;
        }
        else{
          return true;
        }
      }
    }

    return _.filter(vm.fileTypes, function (fileType) {
        return _.includes(file.type, fileType.id);
      }).length > 0;
  }
});
angular.module('userUi').controller('FileCtrl', function FileCtrl($modalInstance,isSize,isType) {
  var vm = this;
  vm.isFileSize = isSize;
  vm.isFileType =isType;
  vm.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});

