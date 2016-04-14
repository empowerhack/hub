(function() {
    'use strict';

    angular
        .module('hubApp')
        .controller('SkillController', SkillController);

    SkillController.$inject = ['$scope', '$state', 'Skill', 'SkillSearch'];

    function SkillController ($scope, $state, Skill, SkillSearch) {
        var vm = this;
        vm.skills = [];
        vm.loadAll = function() {
            Skill.query(function(result) {
                vm.skills = result;
            });
        };

        vm.search = function () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SkillSearch.query({query: vm.searchQuery}, function(result) {
                vm.skills = result;
            });
        };
        vm.loadAll();
        
    }
})();
