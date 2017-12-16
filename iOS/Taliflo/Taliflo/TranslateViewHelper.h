//
//  TLFViewHelper.h
//  taliflo
//
//  Created by NR-Mac on 1/1/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TranslateViewHelper : NSObject

@property (nonatomic) BOOL screenSize4s;

- (instancetype)initWithViewController:(UIViewController *)viewController;
- (void)scrollViewUp:(int)dist;
- (void)scrollViewBackToCenter;
- (void)scrollViewUpForKeyboard:(UIView *)view;

@end
