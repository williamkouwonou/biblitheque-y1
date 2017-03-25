angular.module('app').controller('UserController', ['$rootScope','$location', 'Flash', 'UserService', function ($rootScope,$location, Flash, UserService) {
        console.info("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
        var vm = this;

        vm.register = register;
        vm.update = update;
        vm.chercher = chercher;
        vm.size = 5;
        vm.page = 0;
        vm.mot = '';
        vm.edit = edit;
        vm.mode=1;
        vm.goToPage = goToPage;
        chercher();
        initUser();
        
        function Create() {
            vm.dataLoading = true;
            UserService.Create(vm.user)
                    .then(function (response) {
                        if (!response.error) {
                            vm.dataLoading = false;
                            vm.user = null;
                            vm.user = {nom: '', prenom: '', dateNaissance: null, email: '', login: '', tel: ''};
                            vm.error = {};
                            Flash.create('success', response.message, 0, {class: 'custom-class', id: 'custom-id'}, true);
                        } else {
                            vm.error = response;

                            vm.dataLoading = false;
                            Flash.create('warning', response.message, 0, {class: 'custom-class', id: 'custom-id'}, true);

                        }
                    });
        }
        function register() {
            if (vm.user.id) {
                update();
                  $location.path("/list");
            } else {
                Create();
            }
        }
       vm.registerProfile= function () {
           vm.user=angular.copy($rootScope.userinfo);
          
                update();
           vm.mode=1;
        };
        function update() {
            vm.dataLoading = true;
            UserService.Update(vm.user)
                    .then(function (response) {
                        if (!response.error) {

                            vm.user = {nom: '', prenom: '', dateNaissance: null, email: '', login: '', tel: ''};
                            vm.error = {};
                          
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
        function initUser(){
           
                vm.user = angular.copy(UserService.user);
                
                console.info(JSON.stringify(vm.user));
                UserService.user={nom: '', prenom: '', dateNaissance: null, email: '', login: '', tel: ''};
            
        }
        function edit(id) {

            for (var i = 0; i < vm.users.length; i++) {
                if (vm.users[i].id === id) {
                    UserService.user = angular.copy(vm.users[i]);
                    UserService.user.dateNaissance = new Date(angular.copy(vm.users[i]).dateNaissance);

                    $location.path("/register");
                    break;
                }
            }
        }
    }]);