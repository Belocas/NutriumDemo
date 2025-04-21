package com.example.nutriumdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutriumdemo.data.dao.DatabaseProvider
import com.example.nutriumdemo.data.dto.Professional
import com.example.nutriumdemo.data.repository.ProfessionalsRepository
import com.example.nutriumdemo.databinding.FragmentFirstBinding
import com.example.nutriumdemo.utils.ProfessionalMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), OnProfessionalClickListener {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var arrayInfo = arrayListOf<Professional>()
    var isLoading = false
    var sort:String = "best_match"
    var currentIndex:Int = 0
    lateinit var recyclerView: RecyclerView
    lateinit var professionalsAdapter:ProfessionalsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dropdown = view.findViewById<AutoCompleteTextView>(R.id.dropdownMenu)

        val optionsDropDown = listOf("Best for you", "Rating", "Most popular")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, optionsDropDown)
        dropdown.setAdapter(adapter)
        dropdown.setText(optionsDropDown[0], false)

        dropdown.setOnItemClickListener { parent, _, position, _ ->
            val selecionado = parent.getItemAtPosition(position).toString()
            Toast.makeText(requireContext(), "Selecionou: $selecionado", Toast.LENGTH_SHORT).show()
            professionalsAdapter.cleanData()
            sort = optionsDropDown[position]
            currentIndex = 0
            loadMoreItems(4,optionsDropDown[position])

        }

        GlobalScope.launch {
            var lista =
                ProfessionalsRepository.getInstance().searchProfessionals(4, 0, "best_match")

            println(lista)

            var professionais = ProfessionalsRepository.getInstance().getProfessionalDetails("5")
            println("Professionais " + professionais)
            withContext(Dispatchers.Main) {
                lista?.let { professionalsAdapter.updateData(it.professionals) }
            }
            lista?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    val db = DatabaseProvider.getDatabase(requireContext())
                    var list = lista.professionals.toList()
                    for (item in list){
                        db.professionalDao().insertProfessional(ProfessionalMapper.fromNetwork(item))
                    }

                    /*db.professionalDao()
                        .insertAll(ProfessionalMapper.fromNetworkList(lista.professionals.toMutableList()))*/
                }
            }
        }
        professionalsAdapter = ProfessionalsAdapter(mutableListOf(),this)
        recyclerView = view.findViewById(R.id.myprofessionalsRecyclerView)
        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = professionalsAdapter


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                val visibleThreshold = 2 // Quando faltar 2 itens, já começa a carregar

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    loadMoreItems(0,sort)
                    isLoading = true
                }
            }
        })
    }



    fun loadMoreItems(offset: Int, sort:String) {
        val defaultLoad = 4
        CoroutineScope(Dispatchers.IO).launch {
            val lista = ProfessionalsRepository.getInstance().searchProfessionals(defaultLoad, currentIndex,sort)

            withContext(Dispatchers.Main) {
                lista?.let { professionalsAdapter.updateData(it.professionals) }
                isLoading = false
            }
        }
    }

    override fun onProfessionalClick(id: Int) {
        Toast.makeText(requireContext(), "Clicou em $id", Toast.LENGTH_SHORT).show()

        val newFragment = SecondFragment()
        //action_FirstFragment_to_SecondFragment(newFragment)
        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(userId = id.toString())
        findNavController().navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}