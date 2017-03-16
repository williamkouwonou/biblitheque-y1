angular.module('app').controller('RegisterUserController', ['Flash', 'UserService', function (Flash, UserService) {

        var vm = this;

        vm.register = register;

        function register() {
            vm.dataLoading = true;
            UserService.Create(vm.user)
                    .then(function (response) {
                        if (!response.error) {

                            vm.user = {};
                            vm.error = {};
                            Flash.create('success', response.message, 0, {class: 'custom-class', id: 'custom-id'}, true);
                        } else {



                            vm.error = response;

                            vm.dataLoading = false;
                            Flash.create('warning', response.message, 0, {class: 'custom-class', id: 'custom-id'}, true);

                        }
                    });
        }


    }]);