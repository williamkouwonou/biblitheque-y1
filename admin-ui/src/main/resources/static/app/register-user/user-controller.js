angular.module('app').controller('UserController', ['Flash', 'UserService', function (Flash, UserService) {

        var vm = this;

        vm.register = register;
        vm.chercher = chercher;
        vm.size = 5;
        vm.page = 0;
        vm.mot = '';
        vm.goToPage=goToPage;
        chercher();
        
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
        
        function chercher() {

            UserService.ListbyPage(vm.size, vm.page, vm.mot)
                    .then(function (response) {
                        if (!response.error) {

                            vm.users = response.content;
                            vm.pages = new Array(response.totalPages);
                        } else {


                        }
                    });
        }


        function goToPage(page) {
            vm.page = page;
            vm.chercher();
        }
    }]);