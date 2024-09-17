package sdm.scl.ifsp.FastCalculation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import sdm.scl.ifsp.FastCalculation.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var fragmentGameBinding: FragmentGameBinding
    private lateinit var settings: Settings
    private lateinit var calculationGame: CalculationGame
    private var currentRound: CalculationGame.Round? = null
    private var hits = 0
    private val roundDeadLineHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            play()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            settings = it.getParcelable(Extras.EXTRA_SETTINGS) ?: Settings()
        }
        calculationGame = CalculationGame(settings.rounds)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentGameBinding = FragmentGameBinding.inflate(inflater, container, false)
        val onClickListener = View.OnClickListener {
            val value = (it as Button).text.toString().toInt()
            if (value == currentRound?.answer) {
                hits++
            }
            roundDeadLineHandler.removeMessages(MSG_ROUND_DEADLINE)
            play()
        }
        fragmentGameBinding.apply {
            alternativeOneBt.setOnClickListener(onClickListener)
            alternativeTwoBt.setOnClickListener(onClickListener)
            alternativeThreeBt.setOnClickListener(onClickListener)
        }
        play()
        return fragmentGameBinding.root
    }

    companion object {
        private const val MSG_ROUND_DEADLINE = 0

        @JvmStatic
        fun newInstance(settings: Settings) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Extras.EXTRA_SETTINGS, settings)
                }
            }
    }

    private fun play() {
        currentRound = calculationGame.nextRound()
        if (currentRound != null) {
            fragmentGameBinding.apply {
                "Round: ${currentRound!!.round}/${settings.rounds}".also {
                    roundTv.text = it
                }
                questionTv.text = currentRound!!.question
                alternativeOneBt.text = currentRound!!.alt1.toString()
                alternativeTwoBt.text = currentRound!!.alt2.toString()
                alternativeThreeBt.text = currentRound!!.alt3.toString()
            }
            roundDeadLineHandler.sendEmptyMessageDelayed(MSG_ROUND_DEADLINE, settings.roundInterval)
        } else {
            val intent = Intent(requireContext(), ResultActivity::class.java).apply {
                putExtra(Extras.EXTRA_RESULT, hits)
                putExtra(Extras.EXTRA_SETTINGS, settings)
            }
            startActivity(intent)
            activity?.finish()
        }
    }
}