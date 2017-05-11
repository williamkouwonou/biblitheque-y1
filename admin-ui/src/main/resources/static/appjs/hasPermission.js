/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module('app').directive('hasPermission', function (permissions) {
    return {
        link: function (scope, element, attrs) {
            if (!_.isString(attrs.hasPermission)) {
                throw 'hasPermission value must be a string';
            }
            var value = attrs.hasPermission.trim();
            var notPermissionFlag = value[0] === '!';
            if (notPermissionFlag) {
                value = value.slice(1).trim();
            }

            function toggleVisibilityBasedOnPermission() {
                var hasPermission = permissions.hasPermission(value);
                if (hasPermission && !notPermissionFlag || !hasPermission && notPermissionFlag) {
                    element.show();
                } else {
                    element.hide();
                }
            }

            toggleVisibilityBasedOnPermission();
            scope.$on('permissionsChanged', toggleVisibilityBasedOnPermission);
        }
    };
});
