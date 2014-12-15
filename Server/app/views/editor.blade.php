@extends('layout')

@section('navbar-links')
    <li><a href="{{ URL::route('converter') }}">Converter</a></li>
    <li class="active"><a href="{{ URL::route('editor') }}">Editor</a></li>
@stop

@section('content')
    <script type="text/javascript" src="{{ URL::asset('assets/scripts/editor.js') }}"></script>
    <canvas id='id_canvas' onmousedown='touch(event)'></canvas>

    <div class="container">
    <script>init()</script>
@stop