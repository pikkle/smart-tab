@extends('layout')

@section('navbar-links')
    <li class="active"><a href="{{ URL::route('converter') }}">Converter</a></li>
    <li><a href="{{ URL::route('editor') }}">Editor</a></li>
@stop

@section('content')
    <div class="container theme-showcase" role="main">
        <div class="jumbotron">
            <h1>Ascii Tab Converter</h1>
            <p>Copy and paste a tab in the Ascii Tab Format. Please refer to the documentation:
                <a href="http://en.wikipedia.org/wiki/ASCII_tab">ASCII Tab on Wikipédia</a>
            </p>

            <p>
                Song Name: <br>
                <input type="text" id="name" cols="50" rows="1" placeholder="Hakuna Matata" />
            </p>

            <p>
                Artist: <br>
                <input type="text" id="artist" cols="50" rows="1" placeholder="The Lion King" />
            </p>

            <p>
                Tempo: <br>
                <input type="text" id="tempo" cols="50" rows="1" placeholder="120" />
            </p>


            <button href="#" id="init" class="btn btn-primary" onclick="initialize()">Initialize Tab &raquo;</button>

            <p>Tab:</p>
            <textarea id="tab" cols="110" rows="6"  form="usrform" placeholder="e |-----0------|
B |-----1------|
G |-----0------|
D |-----2------|
A |-----3------|
E |------------|"
                style="font-family:monospace,Courier; resize:none"></textarea>
            <br>
            <button href="#" id="add" class="btn btn-primary" onclick="parser()">Add to tab &raquo;</button>
            <button href="#" id="finish" class="btn btn-primary" onclick="finish()">Finish &raquo;</button>

            <br>
            <p id="errorMessage" style="height: 50px; color: #CD3F3F;"></p>

            <textarea id="result" cols="110" rows="8"  form="usrform" style="font-family:monospace,Courier; resize:none"></textarea>
            <br>
            <p>
            <button href="#" id="upload" class="btn btn-primary">Upload &raquo;</button>
            </p>
            <script src="{{ URL::asset('assets/scripts/converter.js') }}"></script>
        </div>
    </div>

@stop