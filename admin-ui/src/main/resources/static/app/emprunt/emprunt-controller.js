angular.module('app').controller('EmpruntController', ['$rootScope', '$location', 'Flash', 'EmpruntService', function ($rootScope, $location, Flash, EmpruntService) {

        var vm = this;

        vm.register = register;
       
        vm.chercher = chercher;
       
        
        vm.parameters={dateDebut:null, dateFin:null,moCle:'',size:5,page:0};
       
        vm.goToPage = goToPage;
        chercher();
       
        function Create() {
            vm.dataLoading = true;
            EmpruntService.Create(vm.isbn,vm.login)
                    .then(function (response) {
                        if (!response.error) {
                            vm.dataLoading = false;
                            
                            vm.isbn="";
                            vm.login="";
                            vm.error = {};
                            Flash.create('success', response.message, 4000, {class: 'custom-class', id: 'custom-id'}, true);
                        } else {
                            vm.error = response;

                            vm.dataLoading = false;
                            Flash.create('warning', response.message, 4000, {class: 'custom-class', id: 'custom-id'}, true);

                        }
                    });
        }
        function register() {
//            if (vm.emprunt.id) {
//               
//                $location.path("/listemprunt");
//            } else {
                Create();
//            }
        }
       
    
        function chercher() {

            EmpruntService.ListbyPage(vm.parameters)
                    .then(function (response) {
                        if (!response.error) {

                            vm.emprunts = response.content;
                            vm.pages = new Array(response.totalPages);
                        } else {


                        }
                    });
        }
        


        function goToPage(page) {
            vm.parameters.page = page;
            vm.chercher();
        }
        
       
    }]);