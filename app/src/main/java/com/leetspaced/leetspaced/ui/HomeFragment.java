package com.leetspaced.leetspaced.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.leetspaced.leetspaced.MainViewModel;
import com.leetspaced.leetspaced.R;
import com.leetspaced.leetspaced.database.Question;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements QuestionsAdapter.ListItemClickListener, QuestionDetailsDialog.QuestionProvider {

    private static final String TAG = HomeFragment.class.getSimpleName();
    public static final String STATE_SELECTED_TAB_POSITION = "selectedTabPosition";

    private MainViewModel viewModel;
    private Question[] mQuestions;
    private Question lastClickedQuestion;
    private QuestionsAdapter adapter;
    private RecyclerView questionsRecyclerView;
    private TextView emptyScreenMsgTextView;

    Question[] allQuestions;
    Question[] solvedQuestions;
    Question[] confidentQuestions;
    Question[] masteredQuestions;

    int selectedTab = 0;
    String emptyScreenMessage = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            // Get position of the selected tab before rotation
            selectedTab = savedInstanceState.getInt(STATE_SELECTED_TAB_POSITION);
        }

        setupViewModelAndObservers();
        setupRecyclerView(view);
        setupTabLayout(view);
    }

    private void setupTabLayout(@NonNull View view) {
        emptyScreenMsgTextView = view.findViewById(R.id.home_empty_screen_text_view);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.getTabAt(selectedTab).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tab.getPosition();

                // We are using mQuestions here so that we know which was the last question we clicked on
                // based on the tab we are on.
                switch (selectedTab){
                    case 0:
                        mQuestions = allQuestions;
                        adapter.setmQuestions(allQuestions);
                        break;
                    case 1:
                        mQuestions = solvedQuestions;
                        adapter.setmQuestions(solvedQuestions);
                        emptyScreenMessage = getString(R.string.home_solved_empty_screen_message);
                        break;
                    case 2:
                        mQuestions = confidentQuestions;
                        adapter.setmQuestions(confidentQuestions);
                        emptyScreenMessage = getString(R.string.home_confident_empty_screen_message);
                        break;
                    case 3:
                        mQuestions = masteredQuestions;
                        adapter.setmQuestions(masteredQuestions);
                        emptyScreenMessage = getString(R.string.home_mastered_empty_screen_message);
                        break;
                }

                showMessageIfScreenEmpty();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void showMessageIfScreenEmpty() {
        switch (selectedTab){
            case 0:
                emptyScreenMessage = "";
                break;
            case 1:
                emptyScreenMessage = getString(R.string.home_solved_empty_screen_message);
                break;
            case 2:
                emptyScreenMessage = getString(R.string.home_confident_empty_screen_message);
                break;
            case 3:
                emptyScreenMessage = getString(R.string.home_mastered_empty_screen_message);
                break;
        }
        if (mQuestions == null || mQuestions.length == 0 && selectedTab != 0) {
            emptyScreenMsgTextView.setVisibility(View.VISIBLE);
            emptyScreenMsgTextView.setText(emptyScreenMessage);
            questionsRecyclerView.setVisibility(View.GONE);
        }
        else {
            emptyScreenMsgTextView.setVisibility(View.GONE);
            questionsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void setupViewModelAndObservers() {
        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        // We are using mQuestions here so that we know which was the last question we clicked on
        // based on the tab we are on.
        // We are checking the selected tab here because the data might change while we are on the
        // current tab (reviewing a question or updating notes) and the changes need to reflect on
        // the recyclerView and mQuestions selected.
        viewModel.getAllQuestions().observe(getViewLifecycleOwner(), new Observer<Question[]>() {
            @Override
            public void onChanged(Question[] questions) {
                allQuestions = questions;
                if (selectedTab == 0) {
                    mQuestions = allQuestions;
                    adapter.setmQuestions(allQuestions);

                }
            }
        });
        viewModel.getSolvedQuestions().observe(getViewLifecycleOwner(), new Observer<Question[]>() {
            @Override
            public void onChanged(Question[] questions) {
                solvedQuestions = questions;
                if (selectedTab == 1) {
                    mQuestions = solvedQuestions;
                    adapter.setmQuestions(solvedQuestions);
                    showMessageIfScreenEmpty();
                }
            }
        });
        viewModel.getConfidentQuestions().observe(getViewLifecycleOwner(), new Observer<Question[]>() {
            @Override
            public void onChanged(Question[] questions) {
                confidentQuestions = questions;
                if (selectedTab == 2) {
                    mQuestions = confidentQuestions;
                    adapter.setmQuestions(confidentQuestions);
                    showMessageIfScreenEmpty();
                }
            }
        });
        viewModel.getMasteredQuestions().observe(getViewLifecycleOwner(), new Observer<Question[]>() {
            @Override
            public void onChanged(Question[] questions) {
                masteredQuestions = questions;
                if (selectedTab == 3) {
                    mQuestions = masteredQuestions;
                    adapter.setmQuestions(masteredQuestions);
                    showMessageIfScreenEmpty();
                }
            }
        });
    }

    /**
     * A method that sets up the recycler view that displays
     * all the questions.
     *
     * @param view
     */
    private void setupRecyclerView(@NonNull View view) {
        questionsRecyclerView = view.findViewById(R.id.all_questions_recycler_view);
        adapter = new QuestionsAdapter(this);
        questionsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(final int position) {
        lastClickedQuestion = mQuestions[position];
        QuestionDetailsDialog questionDetailsDialog = QuestionDetailsDialog.newInstance();
        questionDetailsDialog.show(getChildFragmentManager(), "QuestionDetailsDialog");
        Log.d(TAG, mQuestions[position].getTitle());
    }

    @Override
    public Question getClickedQuestion() {
        return lastClickedQuestion;
    }

    @Override
    public void updateClickedQuestion(Question question) {
        viewModel.updateQuestion(question);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // Save position of the selected tab before rotation
        outState.putInt(STATE_SELECTED_TAB_POSITION, selectedTab);
        super.onSaveInstanceState(outState);
    }
}