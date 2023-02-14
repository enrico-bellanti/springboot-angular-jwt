import { environment } from 'src/environments/environment';
import { Observable, mergeMap, Subject, takeUntil } from 'rxjs';
import { Injectable, OnDestroy } from '@angular/core';
import { RxStomp, IMessage } from '@stomp/rx-stomp';
import { AuthState } from '../store/auth/auth.state';
import { Store } from '@ngrx/store';
import { getUserId } from '../store/auth/auth.selectors';

const wsPvtChPath = environment.wsPvtChPath;

@Injectable({
  providedIn: 'root'
})
export class WebsocketService extends RxStomp implements OnDestroy {
  private destroy$$ = new Subject<void>();

  constructor(){
    super();
  }

  subScribePrivateTopic(userId: string): Observable<IMessage>{
    const channel = `${wsPvtChPath}${userId}`
    return this.watch(channel)
  }

  ngOnDestroy(): void {
    this.destroy$$.next();
  }


}
