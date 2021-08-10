package com.example.obukeweatherapp

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obukeweatherapp.adapter.CityAdapter
import com.example.obukeweatherapp.models.CityModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class CitiesFragment : Fragment(), OnCityClickListener {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var cityAdapter: CityAdapter
    lateinit var editText: EditText
    lateinit var textView: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_city_list, container, false)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        editText = view.findViewById(R.id.usernameEditText)
        val screenOrientation = resources.configuration.orientation

        if(screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            textView = view.findViewById(R.id.textView)
            savedInstanceState?.let {
                val value = it.getString("ET_VALUE")
                Log.e("SavedInstanceState", "rekreira - $value")
                textView.text = value
                view.requestLayout()
            }
        }

        val cityList = mutableListOf<CityModel>()
        cityList.add(CityModel(0, "London", 51.5074f, 0.1278f))
        cityList.add(CityModel(0, "Amsterdam", 52.3676f, 4.9041f))
        cityList.add(CityModel(0, "Berlin", 52.5200f, 13.4050f))
        getCurrentLocation(cityList)

        cityAdapter = CityAdapter(requireContext(), cityList, this)

        val cityRecycler = view.findViewById<RecyclerView>(R.id.cityListRecycler)
        cityRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        cityRecycler.adapter = cityAdapter


        return view
    }

    private fun getCurrentLocation(cityList: MutableList<CityModel>) {
        var cityModel: CityModel? = null
        if (!checkForLocationPermission()) {
            Toast.makeText(
                requireContext(),
                "You need permission for location.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { locationRequest ->
                val userLatitude = locationRequest.result?.latitude
                val userLongitude = locationRequest.result?.longitude
                Log.e("LOCATION", "LAT: $userLatitude, LNG: $userLongitude")
                userLatitude?.let { nonNullUserLatitude ->
                    userLongitude?.let { nonNullUserLongitude ->
                        var city = CityModel(
                            1,
                            "Neki grad",
                            nonNullUserLatitude.toFloat(),
                            nonNullUserLongitude.toFloat()
                        )
                        cityList.add(city)
                        cityAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private val LOCATION_PERMISSION_CODE = 11

    private fun checkForLocationPermission(): Boolean {
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val fineLocationPermission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        )
        val listPermissionsNeeded = ArrayList<String>()

        if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (listPermissionsNeeded.size > 0) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                listPermissionsNeeded.toArray(arrayOfNulls<String>(listPermissionsNeeded.size)),
                LOCATION_PERMISSION_CODE
            )
            return false
        }
        return true

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("SavedInstanceState", "sejvuje")
        val string: String = editText.text.toString()
        outState.putString("ET_VALUE", string)
    }

    override fun onCityClick(city: CityModel) {

        val screenOrientation = resources.configuration.orientation

        if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            val args = Bundle()
            args.putSerializable("CITY_OBJECT", city)
            val sixteenDayFragment = SixteenDayFragment()
            sixteenDayFragment.arguments = args
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .add(R.id.weatherFragmentContainer, sixteenDayFragment)
                    .addToBackStack("fragment_backstack")
                    .commit()
            }
        } else {
            val args = Bundle()
            args.putSerializable("CITY_OBJECT", city)
            val sixteenDayFragment = SixteenDayFragment()
            sixteenDayFragment.arguments = args
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .add(R.id.rootFragmentContainer, sixteenDayFragment)
                    .addToBackStack("fragment_backstack")
                    .commit()
            }
        }
    }


}