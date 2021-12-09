import { Reducer } from 'react';
import { CombinedState, combineReducers } from 'redux';

import { user } from '@/store/reducer/user'

const comb: Reducer<CombinedState<any>, any> = combineReducers({
    user
});

export default comb;