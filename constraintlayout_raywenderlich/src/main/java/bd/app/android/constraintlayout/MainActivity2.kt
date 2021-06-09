package bd.app.android.constraintlayout

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import bd.app.android.constraintlayout.databinding.Keyframe1Binding


/**
 * Created by Md.Tarikuzzaman on 09-Jun-2021 5:29 PM, ROSC-II MIS Cell, LGED.
 */

class MainActivity2 : AppCompatActivity() {

    private val constraintSet1 = ConstraintSet()
    private val constraintSet2 = ConstraintSet()
    private lateinit var binding: Keyframe1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Keyframe1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.keyframe1)

        constraintSet1.clone(binding.constraintLayout) //1
        constraintSet2.clone(this, R.layout.activity_main) //2

        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            binding.switch1.setText(if (isChecked) R.string.round_trip else R.string.one_way)
        }

        binding.departButton.setOnClickListener {
            //1
            val layoutParams = binding.rocketIcon.layoutParams as ConstraintLayout.LayoutParams
            val startAngle = layoutParams.circleAngle
            val endAngle = startAngle + (if (binding.switch1.isChecked) 360 else 180)

            //2
            val anim = ValueAnimator.ofFloat(startAngle, endAngle)
            anim.addUpdateListener { valueAnimator ->

                //3
                val animatedValue = valueAnimator.animatedValue as Float
                val layoutParams = binding.rocketIcon.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.circleAngle = animatedValue
                binding.rocketIcon.layoutParams = layoutParams

                //4
                binding.rocketIcon.rotation = (animatedValue % 360 - 270)
            }
            //5
            anim.duration = if (binding.switch1.isChecked) 2000 else 1000

            //6
            anim.interpolator = LinearInterpolator()
            anim.start()
        }
    }

    override fun onEnterAnimationComplete() { //1
        super.onEnterAnimationComplete()

        constraintSet2.clone(this, R.layout.activity_main) //2

        //apply the transition
        val transition = AutoTransition() //3
        transition.duration = 1000 //4
        TransitionManager.beginDelayedTransition(binding.constraintLayout, transition) //5

        constraintSet2.applyTo(binding.constraintLayout) //6
    }
}
