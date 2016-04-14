(function() {
    'use strict';

    angular
        .module('hubApp')
        .controller('SkillDetailController', SkillDetailController);

    SkillDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Skill', 'User'];

    function SkillDetailController($scope, $rootScope, $stateParams, entity, Skill, User) {
        var vm = this;
        vm.skill = entity;
        vm.load = function (id) {
            Skill.get({id: id}, function(result) {
                vm.skill = result;
            });
        };
        var unsubscribe = $rootScope.$on('hubApp:skillUpdate', function(event, result) {
            vm.skill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
