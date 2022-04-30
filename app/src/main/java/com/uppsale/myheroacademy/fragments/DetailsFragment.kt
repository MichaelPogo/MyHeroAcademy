package com.uppsale.myheroacademy.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.uppsale.myheroacademy.BuildConfig
import com.uppsale.myheroacademy.MainActivity
import com.uppsale.myheroacademy.viewModels.MainViewModel
import com.uppsale.myheroacademy.R
import com.uppsale.myheroacademy.models.Appearance
import com.uppsale.myheroacademy.models.Connections
import com.uppsale.myheroacademy.models.Powerstats
import com.uppsale.myheroacademy.util.Utils
import kotlinx.android.synthetic.main.fragment_details.*
import java.io.File
import java.io.IOException


class DetailsFragment : Fragment() {
    lateinit var viewModel: MainViewModel
    val args:DetailsFragmentArgs by navArgs()
    private var heroImgBitmap:Bitmap? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (activity as MainActivity).viewModel
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val heroPositionId = args.heroPositionId
        val imgUrl = args.imgUrl
        val heroName = args.heroName
        val publisher = args.publisher
        iv_hero.apply {
            //loading full scaled image with 1 day expiration, saving a bitmap of the image to latter share it to other apps (social media)
            Glide.with(this).asBitmap().load(imgUrl).signature(ObjectKey(Utils.getSignatureIntForADay())).into(object: CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    heroImgBitmap = resource
                    setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    //after load clears we can't use the bitmap anymore
                    heroImgBitmap = null
                }

            })
        }
        tv_name.text = heroName
        tv_publisher.text = "publisher: ${publisher}"
        val hero = viewModel.heroes.value?.get(heroPositionId)
        hero?.let {
            setpowerStats(hero.powerstats)
            setAppearance(hero.appearance)
            setConnections(hero.connections)

            /*getting details from the api because of the home assignment,
            its not a must because we have all the info from the search api
            */
            viewModel.getHeroPowerStats(it.id)
            viewModel.getHeroAppearance(it.id)
            viewModel.getHeroConnections(it.id)

        }

        viewModel.powerstats.observe(viewLifecycleOwner) {
            setpowerStats(it)
        }
        viewModel.appearance.observe(viewLifecycleOwner) {
            setAppearance(it)
        }
        viewModel.connections.observe(viewLifecycleOwner) {
            setConnections(it)
        }

        iv_share.setOnClickListener {
            heroImgBitmap?.let {
                val uri = getLocalBitmapUri(it)
                uri?.let{
                    val intent= Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    intent.putExtra(Intent.EXTRA_TEXT, "${hero?.name} also known as ${hero?.biography?.alterEgos}");
                    intent.type = "plain/text"
                    intent.type = "image/*"
                    intent.setDataAndType(uri,"image/*")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivity(Intent.createChooser(intent,"Share to"))
                }
            }


        }
    }

    fun setpowerStats(powerstats: Powerstats){
        tv_intelligence.text = "intelligence: ${powerstats.intelligence}"
        tv_strength.text = "strength: ${powerstats.strength}"
        tv_speed.text = "speed: ${powerstats.speed}"
        tv_durability.text = "durability: ${powerstats.durability}"
        tv_power.text = "power: ${powerstats.power}"
        tv_combat.text = "combat: ${powerstats.combat}"
    }
    fun setAppearance(appearance: Appearance){
        tv_gender.text = "gender: ${appearance.gender}"
        tv_race.text = "race: ${appearance.race}"
        tv_height.text = "height: ${ appearance.height.joinToString(", ")}"
        tv_weight.text = "weight: ${appearance.weight.joinToString(", ")}"
        tv_eye_color.text = "eye color ${appearance.eyeColor}"
        tv_hair_color.text = "hair color: ${appearance.hairColor}"
    }
    fun setConnections(connections: Connections){
        tv_group_affiliation.text = "group: ${connections.groupAffiliation}"
        tv_relatives.text = "relatives: ${connections.relatives}"
    }

    fun getLocalBitmapUri(bmp: Bitmap): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(
                context?.cacheDir,
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            bmpUri = Uri.fromFile(file)
            //staring from Android 24 sharing media between apps must be with content Uri instead of file Uri
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                bmpUri = FileProvider.getUriForFile(requireContext(),"${BuildConfig.APPLICATION_ID}.provider",file)
            }
        } catch (e: IOException) {
            Log.e(tag, "Failed to getLocalBitmapUri: ", e)
        }

        return bmpUri
    }
}