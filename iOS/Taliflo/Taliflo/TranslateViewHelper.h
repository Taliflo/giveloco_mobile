//
//  TLFViewHelper.h
//  Taliflo
//
//  Created by NR-Mac on 1/1/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TranslateViewHelper : NSObject

@property (nonatomic) BOOL screenSize4s;

- (instancetype)initWithViewController:(UIViewController *)viewController;
- (void)scrollViewUp:(int)dist;
- (void)scrollViewBackToCenter;
- (void)scrollViewUpForKeyboard:(UIView *)view;

@end
