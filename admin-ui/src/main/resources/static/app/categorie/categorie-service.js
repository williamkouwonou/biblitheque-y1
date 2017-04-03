
angular.module('app').factory('CategorieService', ['$http', '$q', function ($http, $q) {
        var factory = {
            Create: Create,
            Update: Update,
            ListbyPage:ListByPage,
            List:List,
            ListAll:ListAll
           
        };

        return factory;


        function Create(c) {
            var deferred = $q.defer();
            $http.post('resource/categorie/create', c)
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
        function Update(c) {
            var deferred = $q.defer();
            $http.post('resource/categorie/update', c)
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
            $http.get('resource/categorie/searchepage/'+size+'/'+page+'?mc='+mot)
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
            $http.get('resource/categorie/searche/?mc='+mot)
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
        function ListAll() {
            var deferred = $q.defer();
            $http.get('resource/categorie/list')
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
