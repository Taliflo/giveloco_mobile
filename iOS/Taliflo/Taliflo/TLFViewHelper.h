//
//  TLFViewHelper.h
//  Taliflo
//
//  Created by NR-Mac on 1/1/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TLFViewHelper : NSObject

@property BOOL screenSizeSmall;

- (instancetype)initWithViewController:(UIViewController *)viewController;
- (void)scrollViewUp:(int)dist;
- (void)scrollViewBackToCenter;

@end
