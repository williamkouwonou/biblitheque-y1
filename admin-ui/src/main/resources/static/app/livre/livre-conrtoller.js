angular.module('app').controller('LivreController', ['$rootScope', '$location', 'Flash', 'LivreService', 'CategorieService', function ($rootScope, $location, Flash, LivreService, CategprieService) {

        var vm = this;

        vm.register = register;
        vm.update = update;
        vm.chercher = chercher;
        vm.size = 5;
        vm.page = 0;
        vm.mot = '';
        vm.edit = edit;
        vm.mode = 1;
        vm.goToPage = goToPage;
        chercher();
    
        getAllCategoies();
        initLivre();
        function Create() {
            vm.dataLoading = true;
            LivreService.Create(vm.livre,vm.categorie)
                    .then(function (response) {
                        if (!response.error) {
                            vm.dataLoading = false;
                            vm.livre = null;
                           
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
            if (vm.livre.id) {
                update();
                $location.path("/listbook");
            } else {
                Create();
            }
        }
        vm.registerProfile = function () {
            vm.livre = angular.copy($rootScope.livreinfo);

            update();
            vm.mode = 1;
        };
        function update() {
            vm.dataLoading = true;
            vm.livre.categorie=null;
            LivreService.Update(vm.livre,vm.categorie)
                    .then(function (response) {
                        if (!response.error) {

                            vm.livre = {titre: '', isbn: '', resume: '', auteurs: '', edition: '', collection: '',dateParustion:null,quantite:0};
                            vm.error = {};

                        } else {
                            vm.error = response;

                            vm.dataLoading = false;
                            Flash.create('warning', response.message, 0, {class: 'custom-class', id: 'custom-id'}, true);

                        }
                    });
        }

        function chercher() {

            LivreService.ListbyPage(vm.size, vm.page, vm.mot)
            
                    .then(function (response) {
                        if (!response.error) {

                            vm.livres = response.content;
                            vm.pages = new Array(response.totalPages);
                        } else {


                        }
                    });
        }
        function getAllCategoies() {

            CategprieService.ListAll()
                    .then(function (response) {
                        if (!response.error) {

                            vm.categories = response;
                        } else {


                        }
                    });
        }


        function goToPage(page) {
            vm.page = page;
            vm.chercher();
        }
        function initLivre() {

            vm.livre = angular.copy(LivreService.livre);

            console.info(JSON.stringify(vm.livre));
            LivreService.livre = {titre: '', isbn: '', resume: '', auteurs: '', edition: '', collection: '',dateParustion:null,quantite:0};

        }
        function edit(id) {

            for (var i = 0; i < vm.livres.length; i++) {
                if (vm.livres[i].id === id) {
                    LivreService.livre = angular.copy(vm.livres[i]);
                    LivreService.livre.dateParution = new Date(angular.copy(vm.livres[i]).dateParution);

                    $location.path("/addbook");
                    break;
                }
            }
        }
    }]);