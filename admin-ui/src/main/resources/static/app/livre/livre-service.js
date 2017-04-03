
angular.module('app').factory('LivreService', ['$http', '$q', function ($http, $q) {
        var factory = {
            Create: Create,
            Update: Update,
            ListbyPage:ListByPage,
            List:List,
            livre:{titre: '', isbn: '', resume: '', auteurs: '', edition: '', collection: '',dateParustion:null,quantite:0}

           
        };

        return factory;


        function Create(livre,categorie) {
            var deferred = $q.defer();
            $http.post('resource/livre/create/'+categorie, livre)
                    .then(
                            function (response) {
                                deferred.resolve(response.data);
                            },
                            function (errResponse) {
                                console.error('Registration faild');
                                deferred.reject(errResponse);
                            }
                    );
            return deferred.promise;
        }
        function Update(livre,categorie) {
            var deferred = $q.defer();
            $http.post('resource/livre/update/'+categorie, livre)
                    .then(
                            function (response) {
                                deferred.resolve(response.data);
                            },
                            function (errResponse) {
                                console.error('update faild');
                                deferred.reject(errResponse);
                            }
                    );
            return deferred.promise;
        }
        function ListByPage(size,page,mot) {
            var deferred = $q.defer();
            console.info("gggggggbbbbbbbbbbbb");
            $http.get('resource/livre/searchepage/'+size+'/'+page+'?mc='+mot)
                    .then(
                            function (response) {
                                deferred.resolve(response.data);
                            },
                            function (errResponse) {
                                console.error(' faild');
                                deferred.reject(errResponse);
                            }
                    );
            return deferred.promise;
        }
   
        function List(mot) {
            var deferred = $q.defer();
            $http.get('resource/livre/searche/?mc='+mot)
                    .then(
                            function (response) {
                                deferred.resolve(response.data);
                            },
                            function (errResponse) {
                                console.error(' faild');
                                deferred.reject(errResponse);
                            }
                    );
            return deferred.promise;
        }
   
    
    }]);
