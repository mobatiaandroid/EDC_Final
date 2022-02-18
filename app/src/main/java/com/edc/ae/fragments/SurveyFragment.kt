package com.edc.ae.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.BaseActivities.HomeBaseUserActivity
import com.edc.ae.R
import com.edc.ae.activity.HomeBaseGuestActivity
import com.edc.ae.activity.SurveyDetailActivity
import com.edc.ae.adapter.SurveyListAdapter
import com.edc.ae.api.RetrofitClient
import com.edc.ae.util.OnItemClickListener
import com.edc.ae.util.PreferenceManager
import com.edc.ae.util.addOnItemClickListener
import com.mobatia.edcsurvey.survey.model.SurveyDataModel
import kotlinx.android.synthetic.main.fragment_survey.*
import kotlinx.coroutines.launch

class SurveyFragment : Fragment() {
    lateinit var surveyListRecycler: RecyclerView
    lateinit var mContext: Context
    lateinit var dataArrayList : ArrayList<SurveyDataModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        initUI()
        getSurveyApi()
        navBtn.setOnClickListener { _ ->

            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }
        }


    }

    private fun initUI()
    {
        surveyListRecycler = requireView().findViewById(R.id.surveyListRecycler) as RecyclerView
        surveyListRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mContext, SurveyDetailActivity::class.java)
                intent.putExtra("mylist", dataArrayList.get(position).questions)
                intent.putExtra("survey_name", dataArrayList.get(position).title)
                intent.putExtra("survey_id", dataArrayList.get(position).id.toString())
                startActivity(intent)
            }

        })
    }
    private fun getSurveyApi() {

      //  dataArrayList= ArrayList()

        lifecycleScope.launch {
            try {

                val call = RetrofitClient.get.getSurvey(
                    "Bearer "+ PreferenceManager.getAccessToken(mContext as Activity),
                    PreferenceManager.getStudentID(mContext as Activity)
                )

                when(call.status){
                    200,201 -> {
                        dataArrayList= ArrayList()
                        dataArrayList.addAll(call.data)
                        var surveyAdapter= SurveyListAdapter(dataArrayList,mContext)
                        surveyListRecycler.adapter=surveyAdapter
                    }
                    else -> {

                    }
                }

            }

            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        getSurveyApi()
    }

}