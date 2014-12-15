<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the Closure to execute when that URI is requested.
|
*/


Route::get('/', array('as' => '/', function() // Home page
{
    return View::make('converter');
}));

Route::get('converter', array('as' => 'converter', function() // Converter page
{
    return View::make('converter');
}));

Route::get('editor', array('as' => 'editor', function() // Editor page
{
	return View::make('editor');
}));

Route::get('getTabs/{query?}', array('as' => 'getTabs', // Get the tab list page
    'uses' => 'TablatureController@getTabs'));

Route::post('uploadTab', array('as' => 'uploadTab', // Post a tablature
    'uses' => 'TablatureController@uploadTab'));

Route::get('tablatures/{file}', array('as' => 'tablatures', // Get a tablature
    'uses' => 'TablatureController@tablature'));
