package ch.beerpro;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Collections;

import ch.beerpro.dummy.DummyContent;
import ch.beerpro.dummy.DummyContent.SearchResult;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SearchResultFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    public SearchResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchresult_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);

            DividerItemDecoration dividerItemDecoration =
                    new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()) {

                        @Override
                        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                            int viewType = parent.getAdapter().getItemViewType(0);

                            if (viewType == SearchResultRecyclerViewAdapter.VIEW_TYPE_NO_SEARCH_HEADER) {
                                super.onDraw(c, parent, state);
                            }

                        }
                    };
            recyclerView.addItemDecoration(dividerItemDecoration);

            recyclerView.setAdapter(new SearchResultRecyclerViewAdapter(DummyContent.ITEMS, Arrays.asList("IPA"),
                    Arrays.asList("Moretti Limone", "Lager Hell"), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(SearchResult item);

        void onSearch(String text);
    }
}
