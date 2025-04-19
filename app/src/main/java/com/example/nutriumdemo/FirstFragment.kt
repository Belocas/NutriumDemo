package com.example.nutriumdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutriumdemo.data.dto.Professional
import com.example.nutriumdemo.data.remote.ProfessionalsApi
import com.example.nutriumdemo.data.repository.ProfessionalsRepository
import com.example.nutriumdemo.databinding.FragmentFirstBinding
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

        val spinner: Spinner = view.findViewById(R.id.spinnerDropdown)

        val options = listOf("Best for you", "Rating", "Most popular")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val itemSelecionado = parent.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Selecionou: $itemSelecionado", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // nada selecionado
            }
        }

        GlobalScope.launch {
            var lista = ProfessionalsRepository.getInstance().getProfessionals()

            println(lista)

            var professionais = ProfessionalsRepository.getInstance().getProfessionalDetails(5)
            println("Professionais " + professionais)
            withContext(Dispatchers.Main) {
                lista?.let { professionalsAdapter.updateData(it.professionals) }
            }
        }


        val dataset = arrayOf("January", "February", "March")
        val customAdapter = (dataset)

        professionalsAdapter = ProfessionalsAdapter(mutableListOf(),this)
        recyclerView = view.findViewById(R.id.myprofessionalsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = professionalsAdapter


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