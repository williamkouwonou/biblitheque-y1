
angular.module('app').factory('UserService', ['$http', '$q', function ($http, $q) {
        var factory = {
            Create: Create,
            Update: Update,
            ListbyPage:ListByPage,
            List:List,
            user:{nom:'',prenom:'',dateNaissance:null,email:'',login:'',tel:'',type:1}
        };

        return factory;


        function Create(user) {
            var deferred = $q.defer();
            $http.post('resource/user/create', user)
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
        function Update(user) {
            var deferred = $q.defer();
            $http.post('resource/user/update/'+user.id, user)
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
            $http.get('resource/user/listpage?page='+page+'&size='+size+'&mot='+mot)
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
            $http.get('resource/user/list?mot='+mot)
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
