import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.suyu.suyu_emu.R
import org.suyu.suyu_emu.databinding.FragmentDriverDownloadBinding

class DriverDownloadFragment : Fragment() {
    // 使用View Binding来访问布局中的视图
    private var _binding: FragmentDriverDownloadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using view binding
        _binding = FragmentDriverDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 设置按钮点击监听器
        binding.buttonDownload1.setOnClickListener {
            // 处理下载驱动1的逻辑
        }
        binding.buttonDownload2.setOnClickListener {
            // 处理下载驱动2的逻辑
        }
        binding.buttonDownload3.setOnClickListener {
            // 处理下载驱动3的逻辑
        }
        binding.buttonDownload4.setOnClickListener {
            // 处理下载驱动4的逻辑
        }
        binding.buttonDownload5.setOnClickListener {
            // 处理下载驱动5的逻辑
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 避免内存泄漏
        _binding = null
    }
}