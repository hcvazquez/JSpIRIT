'use strict';

/* Controllers */

angular.module('spirit.controllers', []).controller('GeneralCtrl', ['$scope', '$rootScope', '$timeout', function($scope, $rootScope, $timeout) {
	$scope.packages = [];
	$scope.relationships = [];
	$scope.colors = [];
    $scope.selectionSmells = [];
    $scope.selectionName = "";

    //Supported smells checkboxes.
		$scope.supportedSmells = [{'name': "BrainClass", 'value': true},
		                    {'name': "BrainMethod", 'value': true},
		                    {'name': "DataClass", 'value': true},
		                    {'name': "DispersedCoupling", 'value': true},
		                    {'name': "FeatureEnvy", 'value': true},
		                    {'name': "GodClass", 'value': true},
		                    {'name': "IntensiveCoupling", 'value': true},
		                    {'name': "RefusedParentBequest", 'value': true},
		                    {'name': "ShotgunSurgery", 'value': true},
		                    {'name': "TraditionBreaker", 'value': true}];



	$scope.updateChart = function(packages, colorHash) {

		d3.select('svg').remove();

		$scope.packagesNames = [];
        $scope.packagesIndex = [];
        for(var i = 0; i < packages.length; i++) {
            $scope.packagesNames[i] = packages[i].name;
            $scope.packagesIndex[$scope.packagesNames[i]] = i;                        
        }
        
        
        var buildMatrix = function() {
            var matrix = [];
            for(var i = 0; i < $scope.packagesNames.length; i++) {
                matrix[i] = [];
                for(var j = 0; j < $scope.packagesNames.length; j++)
                    matrix[i][j] = 0;
            }
            
            var j = 0;
            for(var j = 0; j < $scope.packagesNames.length; j++)
            for(var i = 0; i < $scope.relationships.length; i++)
                if($scope.relationships[i].source === $scope.packagesNames[j])
                    matrix[j][$scope.packagesIndex[$scope.relationships[i].target]] = 1;
            return matrix;
                
        }         
        
        
        // D3 Chart
        var data = {
          packageNames: $scope.packagesNames,
          matrix: buildMatrix(),
            colors: colorHash,
            packages: packages,
            scope: $scope
        };
        
        var chart = d3.chart.dependencyWheel($scope);
        d3.select('#d3chart')
        .datum(data)
        .call(chart);

	}

	var formatSmells = function(smells) {
		var result = "";
		for(var i = 0; i < smells.length; i++) {
			var div = smells[i].split("#");
            result = result.concat("<b>" + div[0] + "</b>: " + "<br>" + div[1] + "<br><br>");
		}
		return "<p style=\"color: white; font-family: monospace; font-size: 12px;\">" + result + "</p>";
	}		                    

	$scope.$watch('selectionSmells', function (newValue, oldValue) {      
        if(newValue != undefined && newValue.length > 0) {
            $('#myFrame').contents().find('html').html(formatSmells(newValue));            	
        }
        else
        	$('#myFrame').contents().find('html').html("");
    });

    $scope.$watch('selectionName', function (newValue, oldValue) {      
        $scope.selection = newValue;
    });


    //Returns > 0 if the element is in the array.
	var hasSmell = function(smells,elem) {
		var arr = [];
		for(var i = 0; i < smells.length; i++)
			arr.push(smells[i].split("#")[0].replace(" ",""));				
		return arr.indexOf(elem);
	}
	
	//Returns all packages with the specified smell.
	$scope.getPackagesWithSmell = function(smell) {
		var result = [];
		for(var i = 0; i < $scope.packages.length; i++) {
			if(hasSmell($scope.packages[i].smells,smell) > -1)
				result.push($scope.packages[i].name);
		}
		return result;
	}

	$scope.useOriginalFormula = true;

	$scope.$watch('useOriginalFormula', function (newValue, oldValue) {      
        if (newValue)
        	$scope.updateChart($scope.packages, $scope.colorHash);
        else        
        	$scope.updateChart($scope.packages2, $scope.colorHash2);
        for(var i = 0; i < $scope.supportedSmells.length; i++)
			$scope.supportedSmells[i].value = true;
    });


	$scope.redrawFiltered = function() {
		var packagesToRemove = null;
		var result = $scope.useOriginalFormula ? angular.copy($scope.packages) : angular.copy($scope.packages2);
		for(var i = 0; i < $scope.supportedSmells.length; i++)
			if($scope.supportedSmells[i].value === false)
				packagesToRemove = $scope.getPackagesWithSmell($scope.supportedSmells[i].name);
		console.log(packagesToRemove);
		for(var j = 0; j < packagesToRemove.length; j++)
			result.splice($scope.packagesIndex[packagesToRemove[j]],1);				
		$scope.updateChart(result, $scope.useOriginalFormula ? $scope.colorHash : $scope.colorHash2);		
	}



	$scope.getData = function() {
		$scope.packages = JSON.parse(getDataFeed(0));
		$scope.relationships =  JSON.parse(getDataFeed(4));		
		$scope.colors =  JSON.parse(getDataFeed(2));		
		$scope.packages2 = JSON.parse(getDataFeed(1));
		$scope.colors2 = JSON.parse(getDataFeed(3));
		$scope.colorHash = [];
		for(var i = 0; i < $scope.colors.length; i++) {
			var c = tinycolor($scope.colors[i].color).toHsl();			
			$scope.colorHash[$scope.colors[i].name] = c.l*100;
		}

		$scope.colorHash2 = [];
		for(var i = 0; i < $scope.colors2.length; i++) {
			var c = tinycolor($scope.colors2[i].color).toHsl();			
			$scope.colorHash2[$scope.colors2[i].name] = c.l*100;
		}

		$scope.updateChart($scope.packages, $scope.colorHash);                
        
	}
	
}])