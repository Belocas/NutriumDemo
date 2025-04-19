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
import com.example.nutriumdemo.data.dto.Professional
import com.example.nutriumdemo.data.repository.ProfessionalsRepository
import com.example.nutriumdemo.databinding.FragmentFirstBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneOffset

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
            loadMoreItems(0,4,optionsDropDown[position])

        }

        GlobalScope.launch {
            var lista = ProfessionalsRepository.getInstance().searchProfessionals(4,0,"best_match")

            println(lista)

            var professionais = ProfessionalsRepository.getInstance().getProfessionalDetails(5)
            println("Professionais " + professionais)
            withContext(Dispatchers.Main) {
                lista?.let { professionalsAdapter.updateData(it.professionals) }
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
                    loadMoreItems(4,0,sort)
                    isLoading = true
                }
            }
        })
    }



    fun loadMoreItems(limit:Int,offset: Int, sort:String) {
        // Simula uma chamada async, substitui por chamada real depois
        CoroutineScope(Dispatchers.IO).launch {
            val lista = ProfessionalsRepository.getInstance().searchProfessionals(limit,offset,sort)

            withContext(Dispatchers.Main) {
                lista?.let { professionalsAdapter.updateData(it.professionals) }
                isLoading = false
            }
        }
    }

    override fun onProfessionalClick(id: Int) {
        Toast.makeText(requireContext(), "Clicou em $id", Toast.LENGTH_SHORT).show()

        val newFragment = SecondFragment()

        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}