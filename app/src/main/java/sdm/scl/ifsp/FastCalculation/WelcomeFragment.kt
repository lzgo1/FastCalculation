package sdm.scl.ifsp.FastCalculation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import sdm.scl.ifsp.FastCalculation.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    private lateinit var fragmentWelcomeBinding: FragmentWelcomeBinding
    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            settings = it.getParcelable(Extras.EXTRA_SETTINGS) ?: Settings()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentWelcomeBinding = FragmentWelcomeBinding.inflate(inflater, container, false)
        "${getString(R.string.welcome)},${settings.playerName}!".also {
            fragmentWelcomeBinding.welcomeTv.text = it
        }
        fragmentWelcomeBinding.playBt.setOnClickListener {
            (context as OnPlayGame).onPlayGame()
        }
        //fragmentWelcomeBinding.welcomeTv.text = "Welcome, ${settings.playerName}"
        return fragmentWelcomeBinding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(settings: Settings) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Extras.EXTRA_SETTINGS, settings)
                }
            }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.restartGameMi).isVisible = false
    }

}