angular.module('app').controller('CategorieController', ['Flash', 'CategorieService', function (Flash, CategorieService) {

        var vm = this;

        vm.register = register;
        vm.update = update;
        vm.chercher = chercher;
        vm.size = 5;
        vm.page = 0;
        vm.mot = '';
        vm.edit = edit;
        vm.mode = 1;
        vm.dataLoading = true;
        vm.goToPage = goToPage;
        chercher();


        function Create() {
            vm.dataLoading = true;
            CategorieService.Create(vm.categorie)
                    .then(function (response) {
                        if (!response.error) {
                            vm.dataLoading = false;
                            vm.categorie = null;
                            vm.categorie = {designation: '', description: ''};
                            vm.error = {};
                            Flash.create('success', response.message, 0, {class: 'custom-class', id: 'custom-id'}, true);
                            chercher();
                        } else {
                            vm.error = response;

                            vm.dataLoading = false;
                            Flash.create('warning', response.message, 0, {class: 'custom-class', id: 'custom-id'}, true);

                        }
                    });
        }
        function register() {
            if (vm.categorie.id) {
                update();
            } else {
                Create();
            }

        }

        function update() {
            vm.dataLoading = true;
            CategorieService.Update(vm.categorie)
                    .then(function (response) {
                        if (!response.error) {

                            vm.categorie = {designation: '', description: ''};
                            vm.error = {};
                            chercher();
                        } else {
                            vm.error = response;

                            vm.dataLoading = false;
                            Flash.create('warning', response.message, 0, {class: 'custom-class', id: 'custom-id'}, true);

                        }
                    });
        }

        function chercher() {

            CategorieService.ListbyPage(vm.size, vm.page, vm.mot)
                    .then(function (response) {
                        if (!response.error) {

                            vm.categories = response.content;
                            vm.pages = new Array(response.totalPages);
                        } else {


                        }
                    });
        }


        function goToPage(page) {
            vm.page = page;
            vm.chercher();
        }

        function edit(id) {

            for (var i = 0; i < vm.categories.length; i++) {
                if (vm.categories[i].id === id) {
                    vm.categorie = angular.copy(vm.categories[i]);

                    break;
                }
            }
        }
    }]);