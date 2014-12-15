<?php

/**
 * Main class that controls the tablatures on the server
 */
class TablatureController extends BaseController {
    const STORAGE_DIRECTORY = "storage/tablatures";
    const URL_DIRECTORY = "tablatures";


    /**
     * Gets a list of all tabs available on the server
     * @param string $query A string containing a search of the user. If empty, gives every tabs in the server
     * @return JSON Response of all tabs with two fields: name and filename.
     */
    public function getTabs($query = '') {
        $tabs = Tablature::select(DB::raw('CONCAT(name, " - ", artist) AS name'), "filename")
            ->where("name", "LIKE", '%'.$query.'%')
            ->orWhere("artist", "LIKE", '%'.$query.'%')
            ->orderBy("name")
            ->get();
        foreach ($tabs as $tab) {
            $tab['filename'] = URL::to('/')."/".self::URL_DIRECTORY."/".$tab['filename'];
        }
        return Response::json($tabs, $status=200, $headers=[], $options=JSON_PRETTY_PRINT);
    }

    /**
     * Uploads the tab upon a POST request with required params:
     * - name : The name of the song
     * - artist : The artist of the song
     * - tab : The json that contains the song
     * @return JSON Response with success 200 (success, tabName, tab, uploadedUrl) or failure 400 (success, errorMessage, tabName, tab)
     */
    public function uploadTab() {
        $name = Input::get('name');
        $artist = Input::get('artist');
        $tab = Input::get('tab');

        $filename = str_random(30);//strtolower(self::clean($name));
        $url = URL::to('/')."/".self::URL_DIRECTORY."/".$filename;

        if(Tablature::where('filename', '=', $filename)->count() == 0){
            try {
                $filepath = app_path()."/".self::STORAGE_DIRECTORY."/".$filename;
                $file = fopen($filepath, "w");
                fwrite($file, $tab);
                fclose($file);
                Tablature::insert(array('name' => $name, 'filename' => $filename, 'artist' => $artist));
            } catch (Exception $e) {
                return Response::json(array('success' => false, 'errorMessage' => 'Error saving the tablature, aborting', 'tabName' => $name, 'tab' => $tab),
                    $status=400, $headers=[], $options=JSON_PRETTY_PRINT);
            }

            return Response::json(array('success' => true, 'tabName' => $name, 'tab' => $tab, 'uploadedUrl' => $url),
                $status=200, $headers=[], $options=JSON_PRETTY_PRINT);
        } else {
            return Response::json(array('success' => false, 'errorMessage' => 'There is already a tablature with that name !', 'tabName' => $name, 'tab' => $tab),
                $status=400, $headers=[], $options=JSON_PRETTY_PRINT);
        }

    }

    /**
     * Gives the file that corresponds to the filename given in parameter
     * @param $filename The name of the file stored in the server
     * @return $this The JSON encoded file that contains the tablature
     */
    public function tablature($filename) {
        $filepath = app_path()."/".self::STORAGE_DIRECTORY."/".$filename;
        $contents = file_get_contents($filepath);
        return Response::make($contents, 200)->header('Content-Type', 'json');
    }

} 